import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.VCARD;

public class JenaTest {
	public static void main(String[] args) {
		// URI declarations
		String temperatureUri = "http://ontology/";
		String relationshipUri = "http://purl.org/vocab/relationship/";

		// Create an empty Model
		Model model = ModelFactory.createDefaultModel();

		// Create a Resource for each family member, identified by their URI
		Resource temperature = model.createResource(temperatureUri+"temperature");
		Resource dewPointTemperature = model.createResource(temperatureUri+"dewPointTemperature");
		Resource effectiveTemperature = model.createResource(temperatureUri+"effectiveTemperature");
		Resource wind = model.createResource(temperatureUri+"wind");
		Resource storm = model.createResource(temperatureUri+"storm");
		
		
		Resource degree = model.createResource(temperatureUri+"degree");
		Resource scale = model.createResource(temperatureUri+"scale");



		// Create properties for the different types of relationship to represent
		Property subclassof = model.createProperty(relationshipUri,"subclassof");
		Property onPropery = model.createProperty(relationshipUri,"onProperty");

//		// Add properties to temperature describing relationships to other sub temperature
		temperature.addProperty(onPropery, degree);
		temperature.addProperty(onPropery, scale);
		
		dewPointTemperature.addProperty(subclassof,temperature);
		effectiveTemperature.addProperty(subclassof,temperature);
		storm.addProperty(subclassof, wind);

		model.write(System.out);
		
		ResIterator temperaturesRes = model.listSubjectsWithProperty(subclassof);

		// Because subjects of statements are Resources, the method returned a ResIterator
		while (temperaturesRes.hasNext()) {

		  // ResIterator has a typed nextResource() method
		  Resource temperatureRes = temperaturesRes.nextResource();
		  // Print the URI of the resource
		  Resource matchResource = temperatureRes.getPropertyResourceValue(subclassof);
		  if(matchResource.getURI().contains("temperature")){
			  System.out.println(temperatureRes.getURI());
			  MatchTemperatureProperty(matchResource,onPropery);
		  }
		}
		
	}
	
	
	private static boolean MatchTemperatureProperty(Resource resource, Property property){
		boolean isMatch = false;
		StmtIterator iterator = resource.listProperties();
		while(iterator.hasNext()){
			Statement statement = iterator.nextStatement();
			System.out.println("object " + statement.getObject());
		}
		return isMatch;
	}
}
