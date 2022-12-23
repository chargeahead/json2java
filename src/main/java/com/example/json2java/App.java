package com.example.json2java;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import com.sun.codemodel.JCodeModel;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String packageName ="com.example.json2pojo";
        File inputJson = new File("/home/demo/Documents/input.json");
        File outputDir= new File("."+"/src/main/resources/output");
        outputDir.mkdirs();
        try {
        	new App().JsonToPojo(inputJson.toURI().toURL(), outputDir, packageName, inputJson.getName().replace(".json", ""));
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    	System.out.println( "Done" );
    }
    
    public void JsonToPojo(URL inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

        jcodeModel.build(outputJavaClassDirectory);
    }
}

