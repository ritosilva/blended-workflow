package pt.ist.socialsoftware.blendedworkflow.engines.domain;

public class AndCondition extends AndCondition_Base {
	
	public AndCondition(Condition one, Condition other) {
		addConditions(one);
		addConditions(other);
	}
	
//	private Condition one;
//	private Condition other;
    
//	public AndCondition(Condition one, Condition other) {
//		this.one = one;
//		this.other = other;
//	}
	
//	public TripleStateBool evaluate() {
//		return this.one.evaluate().AND(this.other.evaluate());
//	}
	
//	@Override
//	public ArrayList<DataInfo> getData() {
//		ArrayList<DataInfo> dataL = new ArrayList<DataInfo>(one.getData());
//		dataL.addAll(other.getData());
//		return dataL;
//	}
//	
//	@Override
//	public ArrayList<DataInfo> getKeyData() {
//		ArrayList<DataInfo> dataL = new ArrayList<DataInfo>(one.getKeyData());
//		dataL.addAll(other.getKeyData());
//		return dataL;
//	}
    
}