package uia.auth.db;

public class AuthFunc {

    private long id;

    private String funcName;

    private Long parentFunc;

    private String funcDescription;

    public AuthFunc() {
    }

    public AuthFunc(AuthFunc data) {
        this.id = data.id;
        this.funcName = data.funcName;
        this.parentFunc = data.parentFunc;
        this.funcDescription = data.funcDescription;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFuncName() {
        return this.funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public Long getParentFunc() {
        return this.parentFunc;
    }

    public void setParentFunc(Long parentFunc) {
        this.parentFunc = parentFunc;
    }

    public String getFuncDescription() {
		return funcDescription;
	}

	public void setFuncDescription(String funcDescription) {
		this.funcDescription = funcDescription;
	}

	@Override
    public AuthFunc clone() {
        return new AuthFunc(this);
    }

    @Override
    public String toString() {
        return this.funcName;
    }
}
