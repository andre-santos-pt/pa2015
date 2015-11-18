package pt.iscde.classdiagram.model;

public enum EVisibility {
	PRIVATE, PACKAGE, PROTECTED, PUBLIC;

	public String toString() {
		switch (this) {
		case PRIVATE:
			return "-";
		case PUBLIC:
			return "+";
		case PROTECTED:
			return "#";
		case PACKAGE:
			return "*";
		default:
			return "?";
		}
	}

}
