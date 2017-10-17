
public class Data {
	Data U;
	Data D;
	Data L;
	Data R;
	Column C;
	//when we create a Data object, we do need its head, which is the field Column, 
	//for other field, we will create them afterwards, so we let them be null for the time being.
	
	//the implicit constructor is necessary when the son class call his constructor 
	public Data(){
		this.U=null;
		this.D=null;
		this.L=null;
		this.R=null;
		this.C=null;
	}
	
	public Data(Column C){
		this.U=null;
		this.D=null;
		this.L=null;
		this.R=null;
		this.C=C;
	}

}
