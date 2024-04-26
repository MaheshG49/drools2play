package co.gm;

import org.drools.core.base.RuleNameEndsWithAgendaFilter;
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
import java.util.ArrayList;
import java.util.List;

public class ApproveParamDrool {
    public static void main(String[] args) throws FileNotFoundException {
        KieServices kieServices = KieServices.Factory.get();

        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = kieServices.newKieFileSystem();
        // You can add your DRL files as InputStream here
        kfs.write("src/main/resources/com/sample/SampleParamApprover.drl", kieServices.getResources().newInputStreamResource(new FileInputStream(new File("src/main/resources/org/example/approverParamRule.drl"))));


        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new RuntimeException("Unable to compile drl\".");
        }
//        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        buildAndFireApprovalParamFormatRule(kieBase);
        buildAndFireApprovalParamParseRule(kieBase);

    }

    private static void buildAndFireApprovalParamFormatRule(KieBase kieBase) {
        KieSession kieSession = kieBase.newKieSession();
        RuleResult ruleResult = addFormatParamsAndGetResults(kieSession);
        String ruleFilter = "Approval param format rule";
//        kieSession.addEventListener(new DebugRuleRuntimeEventListener());
        int i = kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter(ruleFilter));
//        int i = kieSession.fireAllRules();
        System.out.println("fire rules value  " + i);
        System.out.println(ruleResult);
    }

    private static RuleResult addFormatParamsAndGetResults(KieSession kieSession) {
        String paramFormat = "approverLevel=approvalAmount;{0}={1};";
        InvoiceApprovalTier invoiceApprovalTier = new InvoiceApprovalTier(1, 100);
        List<InvoiceApprovalTier> tiers = new ArrayList<>(List.of(invoiceApprovalTier));
        RuleResult ruleResult = new RuleResult();
        kieSession.insert(paramFormat);
        kieSession.insert(tiers);
        kieSession.insert(ruleResult);
        return ruleResult;
    }

    private static void buildAndFireApprovalParamParseRule(KieBase kieBase) {
        KieSession kieSession = kieBase.newKieSession();
        RuleResult ruleResult = addParseParamsAndGetResults(kieSession);
        String ruleFilter = "Approval param parse rule";
//        kieSession.addEventListener(new DebugRuleRuntimeEventListener());
        int i = kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter(ruleFilter));
//        int i = kieSession.fireAllRules();
        System.out.println("fire rules value  " + i);
        System.out.println(ruleResult);
    }


    private static RuleResult addParseParamsAndGetResults(KieSession kieSession) {
        String paramFormat = "approverLevel=approvalAmount;{0}={1};";
        String fromattedParam = "1=1000";
        InvoiceApprovalTier invoiceApprovalTier = new InvoiceApprovalTier(1, 100);
        List<String> params = new ArrayList<>(List.of(paramFormat,fromattedParam));
        ArrayList<InvoiceApprovalTier> tiers = new ArrayList<>();
        RuleResult ruleResult = new RuleResult();
        ruleResult.setRuleResult(tiers);
        kieSession.insert(params);
        kieSession.insert(ruleResult);
        return ruleResult;
    }
}
