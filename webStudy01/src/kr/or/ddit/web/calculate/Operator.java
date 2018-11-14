package kr.or.ddit.web.calculate;


public enum Operator {
	
	ADD("+", (leftOper, rightOper)->{return leftOper + rightOper;}),
	MINUS("-", (e,f)->{return e-f;}),
	MULTIPLY("*", (a, b)->{return a*b;}),
	DIVIDE("/", (c, d)->{return c/d;});
	
	private String sign;
	private RealOperator realOperator;
	
	Operator(String sign, RealOperator realOperator) {
		this.sign = sign;
		this.realOperator = realOperator;
	}
	public String getSign(){
		return this.sign;
	}
	public int operate(int leftOper, int rightOper) {
		return realOperator.operate(leftOper, rightOper);
	}
}
