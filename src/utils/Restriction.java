package utils;
import logic.Landuse;
public class Restriction {

	private String req;  // MUST HAVE | CAN HAVE | MUST NOT HAVE
	private String type; // DISTANCE | LEANING | WIDTH | HEIGHT | ?PRICE?
	private String arithmetics; // MORE THAN | MORE OR THE SAME AS | LESS THAN | LESS OR THE SAME AS | EXACTLY
	private double value;  // 
	private Landuse from;
	private Landuse to;


	public Restriction(String req, String type, String arithmetics, double value, Landuse to) {
		super();


		//Requirements verifications

		if(req.equals("MUST HAVE")) {
			this.req = req;
		}
		else if(req.equals("MUST NOT HAVE")) {
			this.req = req;
		}
		else if(req.equals("CAN HAVE")) {
			this.req = req;
		}
		else {
			System.out.println("Requirement typed: " + req);
			System.out.println("Error: Requirement not available. Try \"MUST HAVE\", \"MUST NOT HAVE\" or \"CAN HAVE\".");
			return;
		}

		//Type verifications
		
		if(type.equals("distance")) {
			this.type = type;
		}		
		else if(type.equals("leaning")) {
			this.type = type;
		}
		else if(type.equals("width")) {
			this.type = type;
		}
		else if(type.equals("height")) {
			this.type = type;
		}
		else if(type.equals("price")) {
			this.type = type;
		}
		else {
			System.out.println("Type choosen: " + type);
			System.out.println("Error: Invalid Type. Try \"distance\" \"leaning\", \"width\", \"height\" or \"price\".");
			return;
		}

		//Arithmetics verification

		if(arithmetics.equals("MORE THAN")) {
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("MORE OR THE SAME AS")) {
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("LESS THAN") ){
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("LESS OR THE SAME AS")) {
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("EXACTLY")) {
			this.arithmetics = arithmetics;
		}
		else {
			System.out.println("Arithmetic Comparison: " + arithmetics);
			System.out.println("Error: Arithmetic Comparison not valid. Please pick one from \"MORE THAN\", \"MORE OR THE SAME AS \", \"LESS THAN\", \"LESS OR THE SAME AS\" or \"EXACTLY\".");
			return;
		}

		this.to = to;

		//Value verification

		if(value >= 0)
			this.value = value;
		else
			System.out.println("Error: Value must be higher than 0");


	}

	public Restriction(String req, String type, String arithmetics, double value)
	{
		super();

		//Requirements verifications

		if(req.equals("MUST HAVE")) {
			this.req = req;
		}
		else if(req.equals("MUST NOT HAVE")) {
			this.req = req;
		}
		else if(req.equals("CAN HAVE")) {
			this.req = req;
		}
		else {
			System.out.println("Requirement typed: " + req);
			System.out.println("Error: Requirement not available. Try \"MUST HAVE\", \"MUST NOT HAVE\" or \"CAN HAVE\".");
			return;
		}

		//Type verifications

		if(type.equals("distance")) {
			this.type = type;
		}
		else if(type.equals("leaning")) {
			this.type = type;
		}
		else if(type.equals("width")) {
			this.type = type;
		}
		else if(type.equals("height")) {
			this.type = type;
		}
		else if(type.equals("price")) {
			this.type = type;
		}
		else {
			System.out.println("Type choosen: " + type);
			System.out.println("Invalid Type. Try \"distance\", \"leaning\", \"width\", \"height\" or \"price\".");
			return;
		}

		//Arithmetics verification

		if(arithmetics.equals("MORE THAN")) {
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("MORE OR THE SAME AS")) {
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("LESS THAN") ){
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("LESS OR THE SAME AS")) {
			this.arithmetics = arithmetics;
		}
		else if(arithmetics.equals("EXACTLY")) {
			this.arithmetics = arithmetics;
		}
		else {
			System.out.println("Arithmetic Comparison: " + arithmetics);
			System.out.println("Error: Arithmetic Comparison not valid. Please pick one from \"MORE THAN\", \"MORE OR THE SAME AS \", \"LESS THAN\", \"LESS OR THE SAME AS\" or \"EXACTLY\".");
			return;
		}

		//Value verification

		if(value >= 0)
			this.value = value;
		else
			System.out.println("Error: Value must be higher than 0");

	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	public String getArithmetics() {
		return arithmetics;
	}

	public void setArithmetics(String arithmetics) {
		this.arithmetics = arithmetics;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Landuse getFrom() {
		return from;
	}

	public void setFrom(Landuse from) {
		this.from = from;
	}

	public Landuse getTo() {
		return to;
	}

	public void setTo(Landuse to) {
		this.to = to;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void print()
	{
		System.out.println();
		System.out.println("Restriction:");
		System.out.println(this.req);
		System.out.println(this.type);
		System.out.println(this.arithmetics);
		System.out.println(this.value);
		System.out.println();
	}



}

