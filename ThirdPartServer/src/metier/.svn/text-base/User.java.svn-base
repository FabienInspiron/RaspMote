package metier;

public class User {
	private String nom;
	private String mdp;
	
	/**
	 * Level of privileges
	 * 0 -> none
	 * 1 -> privilege 1
	 * 2 -> privilege 2
	 * ...
	 * 10 -> privilege admin
	 */
	private int privilege;
	
	public User(String nom, String mdp) {
		super();
		this.nom = nom;
		this.mdp = mdp;
		privilege = 0;
	}
	
	public String getNom() {
		return nom;
	}

	public String getMdp() {
		return mdp;
	}

	@Override
	public boolean equals(Object obj) {
		User use = (User) obj;
		
		if(!nom.equals(use.getNom()))
			return false;
		
		if(!mdp.equals(use.getMdp()))
			return false;
		
		return true;
	}
	
	public void setPrivilege(int priv){
		privilege = priv;
	}
	
	public void setAdmin(){
		privilege = 10;
	}
}
