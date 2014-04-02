package at.binarycheese.binaryeframework.StatementExcecution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import at.binarycheese.binaryeframework.StatementExcecution.DML.SelectAll;
import at.binarycheese.binaryeframework.StatementExcecution.DML.SelectSingle;
import at.binarycheese.binaryeframework.StatementExcecution.DML.StatementType;

public class QueryStatementExecution<T> extends StatementExcecution<T> {
	public QueryStatementExecution(PreparedStatement statement, StatementType type) {
		super(statement);
		// TODO Auto-generated constructor stub
	}

	public List<T> execute() {
		try {
			ResultSet resultSet = super.statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(type.getClass()==SelectSingle.class){
			
		}
		if(type.getClass() == SelectAll.class){
			
		}
		return null;
	}
}
