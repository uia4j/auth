package uia.auth;

import java.util.ArrayList;
import java.util.List;

import uia.auth.AuthValidator.AccessType;

public class AuthFuncNode {
	
	private int level;

	private List<AuthFuncNode> nodes;
	
	private long id;
	
	private String name;
	
	private String description;
	
	private String args;
	
	private long parentId;
	
	private AccessType accessType;
	
	private String accessStrategy;
	
	private int accessLevel;
	
	private String accessPoint;
	
	public AuthFuncNode() {
		this.level = 1;
		this.nodes = new ArrayList<AuthFuncNode>();
	}
	
	public AuthFuncNode(long id, String name, String description, String args, long parentId) {
		this.level = 1;
		this.nodes = new ArrayList<>();
		this.id = id;
		this.name = name;
		this.description = description;
		this.args = args;
		this.parentId = parentId;
		this.accessType = AccessType.UNKNOWN;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getId() {
		return this.id;
	}

	public boolean isFirst() {
		return this.parentId <= 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getArgs() {
		return args;
	}

	public List<AuthFuncNode> getNodes() {
		return this.nodes;
	}

	public AccessType getAccessType() {
		return this.accessType;
	}

	public void setAccessType(AccessType accessType, String accessStrategy) {
		walkAccess(accessType, accessStrategy, this.level, "Y");
	}

	public int getAccessLevel() {
		return this.accessLevel;
	}

	public String getAccessStrategy() {
		return this.accessStrategy;
	}
	
	public String getAccessPoint() {
		return this.accessPoint;
	}

	private void walkAccess(AccessType accessType, String accessStrategy, int accessLevel, String accessPoint) {
		this.accessPoint = accessPoint;
		if(accessLevel > this.accessLevel) {
			this.accessType = accessType;
			this.accessStrategy = accessStrategy;
			this.accessLevel = accessLevel;
		}
		else if(accessLevel == this.accessLevel) {
			if(accessType.priority > this.accessType.priority) {
				this.accessType = accessType;
				this.accessStrategy = accessStrategy;
				this.accessLevel = accessLevel;
			}
		}
		this.nodes.stream().forEach(n -> n.walkAccess(accessType, accessStrategy, accessLevel, "N"));
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public void println(String prefix) {
		System.out.println(String.format("%s%s.%s - %s(%s)", prefix, this.id, this.name, this.accessType, this.accessStrategy));
		this.nodes.forEach(n -> {
			n.println(prefix + "  ");
		});
	}
}
