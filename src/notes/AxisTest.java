package notes;

import java.awt.PageAttributes.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;
import javax.xml.ws.Response;

import org.glassfish.jersey.client.JerseyWebTarget;
import org.junit.Test;

public class AxisTest {

	@Test
	public void chainRequests() throws JsonProcessingException {
	    JerseyWebTarget target = getTarget(true);
	    Response response = target.request().header("userId","abcd").get();
	    int count = response.readEntity(List.class).size();
	    System.out.println(count);
	    String responseJson = "{\"count\":"+count+"}";
	    target = getTarget(false);
	    Response response1 = target.request().header("userId","abcd").header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON)
	            .post(Entity.entity(responseJson,MediaType.APPLICATION_JSON),Response.class);
	    System.out.println(response1.readEntity(String.class));

	}

	@Test
	public void chainRequests2() throws JsonProcessingException {
	    JerseyWebTarget target = getTarget(true);
	    List<ProductTemp> response = target.request().header("userId","abcd").get(new GenericType<List<ProductTemp>>(){});
	    long count = response.stream().
	            filter(productTemp -> productTemp.getEndDate() == null
	                    || productTemp.getEndDate().equals(new Date())
	                    || productTemp.getEndDate().after(new Date()))
	             .count();

	    String responseJson = "{\"count\":"+count+"}";
	    target = getTarget(false);
	    Response response1 = target.request().header("userId","abcd").header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON)
	            .post(Entity.entity(responseJson,MediaType.APPLICATION_JSON),Response.class);
	    System.out.println(response1.readEntity(String.class));

	}

	@Test
	public void chainRequests3() throws JsonProcessingException ,Exception{
	    JerseyWebTarget target = getTarget(true);
	    List<ProductTemp> response = target.request().header("userId","abcd").get(new GenericType<List<ProductTemp>>(){});

	    Map<String,Long> validProducts = response.stream().
	            filter(productTemp -> (productTemp.getEndDate() == null
	                    || productTemp.getEndDate().equals(new Date())
	                    || productTemp.getEndDate().after(new Date()))
	                    && (productTemp.getStartDate().before(new Date()) || productTemp.getStartDate().equals(new Date()))
	            )
	            .collect(groupingBy(ProductTemp::getCategory,Collectors.counting()));


	    String responseJson = new ObjectMapper().writeValueAsString(validProducts);
	    System.out.println(responseJson);
	    target = getTarget(false);
	    Response response1 = target.request().header("userId","abcd").header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON)
	            .post(Entity.entity(responseJson,MediaType.APPLICATION_JSON),Response.class);
	    System.out.println(response1.readEntity(String.class));

	}

	@Test
	public void chainRequests4() throws JsonProcessingException ,Exception{
	    JerseyWebTarget target = getTarget(true);
	    List<ProductTemp> response = target.request().header("userId","abcd").get(new GenericType<List<ProductTemp>>(){});

	    Integer money = response.stream().
	            filter(productTemp -> (productTemp.getEndDate() == null
	                    || productTemp.getEndDate().equals(new Date())
	                    || productTemp.getEndDate().after(new Date()))
	                    && (productTemp.getStartDate().before(new Date()) || productTemp.getStartDate().equals(new Date()))
	            )
	            .mapToInt(ProductTemp::getPrice).sum();


	    String responseJson = "{\"totalValue\":"+new ObjectMapper().writeValueAsString(money)+"}";
	    System.out.println(responseJson);
	    target = getTarget(false);
	    Response response1 = target.request().header("userId","abcd").header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON)
	            .post(Entity.entity(responseJson,MediaType.APPLICATION_JSON),Response.class);
	    System.out.println(response1.readEntity(String.class));

	}
	
	private JerseyWebTarget getTarget(boolean input) {
	    java.lang.String uri = String.format("opurl");
	    if(input)
	        uri = java.lang.String.format("ipurl");

	    return client.target(uri);
	}
	
}
