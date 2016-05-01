package Controllers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Models.ActivatedFluxsModel;
import Views.ActivatedFluxsView;
import Views.AggregatorView;

public class ActivatedFluxsController extends AggregatorController {
	
	private ActivatedFluxsModel[]   model;
	
	public ActivatedFluxsController(AggregatorView superView){
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.model = mapper.readValue(contactAPI("/activated", "GET"), ActivatedFluxsModel[].class);
			new ActivatedFluxsView(model, this, superView);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
