/**
 *  Copyright (c) 2016, Carnegie Mellon University.  All Rights Reserved.
 */

import java.io.*;

/**
 *  The AND operator for all retrieval models.
 */
public class QrySopAnd extends QrySop {

  /**
   *  Indicates whether the query has a match.
   *  Should match all of the query arguments for AND operator.
   *  @param r The retrieval model that determines what is a match
   *  @return True if the query matches, otherwise false.
   */
  public boolean docIteratorHasMatch (RetrievalModel r) {
    return this.docIteratorHasMatchAll (r);
  }

  /**
   *  Get a score for the document that docIteratorHasMatch matched.
   *  @param r The retrieval model that determines how scores are calculated.
   *  @return The document score.
   *  @throws IOException Error accessing the Lucene index
   */
  public double getScore (RetrievalModel r) throws IOException {
    if (r instanceof RetrievalModelUnrankedBoolean) {
      return this.getScoreUnrankedBoolean (r);
    } else if ( r instanceof RetrievalModelRankedBoolean) {
    	return this.getScoreRankedBoolean(r);
    } else {
      throw new IllegalArgumentException
        (r.getClass().getName() + " doesn't support the AND operator.");
    }
  }
  
  /**
   *  getScore for the UnrankedBoolean retrieval model.
   *  @param r The retrieval model that determines how scores are calculated.
   *  @return The document score.
   *  @throws IOException Error accessing the Lucene index
   */
  private double getScoreUnrankedBoolean (RetrievalModel r) throws IOException {
    if (! this.docIteratorHasMatchCache()) {
      return 0.0;
    } else {
      return 1.0;
    }
  }
  
  /**
   *  getScore for the RankedBoolean retrieval model.
   *  @param r The retrieval model that determines how scores are calculated.
   *  @return The document score.
   *  @throws IOException Error accessing the Lucene index
   */
  private double getScoreRankedBoolean (RetrievalModel r) throws IOException {
	  if (! this.docIteratorHasMatchCache()) {
	      return 0.0;
	  } else {
	      return getRankedScore();
	  }
	  
  }
  
  /**
   * Implemented the abstract method getRankedScore in the Qry class
   * Get the min matched score in the query arguments for AND operator
   * @return The document score
   * @throws IOException
   */
  @Override
  public double getRankedScore(){
	  double min_score = Double.MAX_VALUE;
	  int matched_docID = this.docIteratorGetMatch();
	  for(int i=0; i<this.args.size(); i++){
		  QrySop qrysop_cur = (QrySop) this.args.get(i);
		  if(this.docIteratorHasMatchCache() && matched_docID == this.args.get(i).docIteratorGetMatch()){
			  if(qrysop_cur.getRankedScore() < min_score){
				  min_score = qrysop_cur.getRankedScore();
			  }
		  }
	  }
	  return min_score;
  }

}
