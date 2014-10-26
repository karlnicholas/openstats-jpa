package openstats.client.openstates;


public interface TestAction extends ReadAction {
	public void loadBulkData() throws Exception;
	public boolean testId(String bill_id);
	public int testAction(String chamber, String act);
}
	
