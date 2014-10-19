package openstats.client.les;

public class AuthorStats {

	public AuthorStats() {
		billData = new long[3][];
		for ( int i=0; i<3; ++i ) {
			billData[i] = new long[4];
			for ( int j=0;j<4;++j) {
				billData[i][j] = 0;
			}
		}
	}
	
	public long billData[][];
	public int cmember = 0;
	public int cvchair = 0;
	public int cchair = 0;
	public int leader = 0;
	public int officeScore = -1;
	public double les = 0.0;
}

