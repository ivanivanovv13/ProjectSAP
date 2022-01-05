package Model;

public class Category {
	private String id;
	private String name;
	private String administratorId;

	public Category(String id, String name, String administratorId) {
		super();
		this.id = id;
		this.name = name;
		this.administratorId = administratorId;
	}

	public String getAdministratorId() {
		return this.administratorId;
	}

	public void setAdministratorId(String administratorId) {
		this.administratorId = administratorId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
