// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package openaiconnector.actions;

import static java.util.Objects.requireNonNull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import genaicommons.proxies.ENUM_ContentType;
import com.mendix.systemwideinterfaces.core.UserAction;

public class ImageResponse_PrepareForImportMapping extends UserAction<java.lang.String>
{
	private final java.lang.String ImageResponse;

	public ImageResponse_PrepareForImportMapping(
		IContext context,
		java.lang.String _imageResponse
	)
	{
		super(context);
		this.ImageResponse = _imageResponse;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		// BEGIN USER CODE
		try {
			requireNonNull(ImageResponse, "ImageResponse is required.");
			
			rootNode = MAPPER.readTree(ImageResponse);
			setImageFileContent();
			
			return MAPPER.writeValueAsString(rootNode);
			
		} catch (Exception e) {
			throw e;
		}
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "ImageResponse_PrepareForImportMapping";
	}

	// BEGIN EXTRA CODE
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private JsonNode rootNode;
	
	
	private void setImageFileContent() throws JsonProcessingException {
		JsonNode dataArray = rootNode.path("data");
		for(JsonNode data : dataArray) {
			 setFileContent(data);
		}
		((ObjectNode) rootNode).set("data", dataArray);
	}
	
	
	private void setFileContent(JsonNode data) throws JsonProcessingException {
		JsonNode imageURL = data.path("url");
		JsonNode imageB64 = data.path("b64_json");
		
		if(!imageURL.isNull() && !imageURL.asText().isBlank()) {
			((ObjectNode) data).put("fileContentType", ENUM_ContentType.Url.name());
			((ObjectNode) data).put("fileContent", imageURL.asText());
		}
		else if(!imageB64.isNull() && !imageB64.asText().isBlank()){
			((ObjectNode) data).put("fileContentType", ENUM_ContentType.Base64.name());
			((ObjectNode) data).put("fileContent", imageB64.asText());
		}
	}
	
	// END EXTRA CODE
}
