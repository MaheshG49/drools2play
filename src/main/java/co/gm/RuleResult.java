package co.gm;

public class RuleResult {

  // Result of the rule will be stored in this object
  private Object ruleResult;

  public Object getRuleResult() {
    return ruleResult;
  }

  public void setRuleResult(Object ruleResult) {
    this.ruleResult = ruleResult;
  }

  @Override
  public String toString() {
    return "RuleResult{" +
            "ruleResult=" + ruleResult +
            '}';
  }
}
