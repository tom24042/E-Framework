package at.binarycheese.binaryeframework.StatementExcecution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public abstract class StatementExcecution<T> {
	protected PreparedStatement statement;
	public StatementExcecution(PreparedStatement statement){
		this.statement = statement;
	}
}
