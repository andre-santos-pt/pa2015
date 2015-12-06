package pa.iscde.javadoc.internal;

public interface JavaDocTagI {
	
	/**
	 * Define o nome do cabeçalho da Tag na View
	 * @return Header Name
	 */
	public String getHeaderName();
	
	/**
	 * Define o nome da Tag da anotação
	 * @return Nome da Tag
	 */
	public String getTagName();

}