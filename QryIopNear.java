import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *  The NEAR operator for all retrieval models.
 */
public class QryIopNear extends QryIop{
	private int distance = 0; //the maximum distance between two terms in a query
	
	/**
	 * Constructor for QryIopNear
	 * @param dist
	 */
	public QryIopNear(int dist){
		this.distance = dist;
	}
	
	/**
	 * Get the location list
	 * @param q
	 * @return
	 */
	public Vector<Integer> getLocationList(QryIop q){
		Vector<Integer> locationList = new Vector<Integer>();
		locationList = q.docIteratorGetMatchPosting().positions;
		return locationList;
	}
	
	/**
	 * 
	 */
	public void evaluate(){
		InvList invList = new InvList(this.getField());
		if(this.args.size() == 0){
			return;
		}
		
	}

	@Override
	public double getRankedScore() {
		// TODO Auto-generated method stub
		return 0;
	}

}
