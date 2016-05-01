package Controllers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Models.ActivatedFluxsModel;
import Models.NewsModel;
import Views.ActivatedFluxsView;
import Views.AggregatorView;
import Views.NewsView;

public class NewsController extends AggregatorController {
	private NewsModel[]   model;
	
	public NewsController(AggregatorView superView){
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			contactAPI("/update", "POST", true);
			this.model = mapper.readValue(contactAPI("/news", "GET"), NewsModel[].class);
			if (this.model != null)
				new NewsView(model, this, superView);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
