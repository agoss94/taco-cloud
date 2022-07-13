package tacos;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientsByIdConverter implements Converter<String, Ingredient>{

	private IngredientRepository repo;


	public IngredientsByIdConverter(IngredientRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public Ingredient convert(String id) {
		return repo.findById(id).orElse(null);
	}
}
