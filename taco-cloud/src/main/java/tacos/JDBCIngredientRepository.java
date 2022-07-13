package tacos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient.Type;

@Repository
public class JDBCIngredientRepository implements IngredientRepository {

	private JdbcTemplate template;

	@Autowired
	public JDBCIngredientRepository(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Ingredient> findAll() {
		return template.query("select id, name, type from Ingredient", this::mapRowToIngredient);
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		List<Ingredient> results = template.query("select id, name, type from Ingredient where id=?",
				this::mapRowToIngredient, id);
		return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		template.update("insert into Ingredient (id, name, type) values (?, ?, ?)", ingredient.getId(),
				ingredient.getName(), ingredient.getType().toString());
		return ingredient;
	}

	private Ingredient mapRowToIngredient(ResultSet resultset, int rowNum) throws SQLException {
		return new Ingredient(resultset.getString("id"), resultset.getString("name"),
				Type.valueOf(resultset.getString("type")));
	}
}
