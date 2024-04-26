package co.gm;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
//import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class DroolsSample {
    public static void main(String[] args) throws FileNotFoundException {
        KieServices kieServices = KieServices.Factory.get();

        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = kieServices.newKieFileSystem();
        // You can add your DRL files as InputStream here
        kfs.write("src/main/resources/com/sample/Sample1.drl", kieServices.getResources().newInputStreamResource(new FileInputStream(new File("src/main/resources/org/example/Sample.drl"))));


        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new RuntimeException("Unable to compile drl\".");
        }
//        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession kieSession = kieBase.newKieSession();
//        kieSession.addEventListener(new DebugRuleRuntimeEventListener());
        kieSession.insert(12);
        int i = kieSession.fireAllRules();
        System.out.println("fire rules value  "+ i);


    }
}
