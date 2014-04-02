package at.binarycheese.binaryeframework.StatementExcecution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import at.binarycheese.binaryeframework.StatementExcecution.DML.StatementType;
import at.binarycheese.binaryeframework.StatementExcecution.DML.Delete;
import at.binarycheese.binaryeframework.StatementExcecution.DML.Insert;
import at.binarycheese.binaryeframework.StatementExcecution.DML.Update;

public class DMLStatementExecution<T> extends StatementExcecution<T> {
	public DMLStatementExecution(PreparedStatement statement, StatementType dml) {
		super(statement);
	}

	public int execute(T t) throws EFrameWorkUserIsIdiotException {
		if (dml.getClass() == Insert.class) {

		}
		if (dml.getClass() == Update.class) {

		}
		if (dml.getClass() == Delete.class) {

		}
		try {
			int effectedRowCount = super.statement.executeUpdate();
			return executePost(effectedRowCount);
		} catch (SQLException e) {
			throw new EFrameWorkUserIsIdiotException("Failed at insert");
		}
	}

	public int executePost(int effectedRowCount)
			throws EFrameWorkUserIsIdiotException {
		if (effectedRowCount == 0) {
			throw new EFrameWorkUserIsIdiotException(
					"Insert did not insert a row.");
		} else if (effectedRowCount != 1) {
			throw new EFrameWorkUserIsIdiotException(
					"Insert inserted more then one row.");
		}
		return effectedRowCount;
	}
}
