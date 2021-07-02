package com.graphql.pojo;

/**
 * This is the main POJO class for complete query and JSON variable
 * @author subratosarkar
 *
 */
public class GraphQLQuery {

	private String query;
	private Object variables;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Object getVariables() {
		return variables;
	}
	public void setVariables(Object variable) {
		this.variables = variable;
	}
	
}
