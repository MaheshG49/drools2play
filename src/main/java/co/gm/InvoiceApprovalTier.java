package co.gm;

public class InvoiceApprovalTier {
    private  int approvalLevel_;
    private long approvalAmount_;

    public static InvoiceApprovalTier newBuilder() {

        return new InvoiceApprovalTier(-1,-1);
    }

    public InvoiceApprovalTier(int approvalLevel_, long approvalAmount_) {
        this.approvalLevel_ = approvalLevel_;
        this.approvalAmount_ = approvalAmount_;
    }

    public int getApprovalLevel() {
        return approvalLevel_;
    }

    public long getApprovalAmount() {
        return approvalAmount_;
    }

    public void setApprovalLevel(int approvalLevel_) {
        this.approvalLevel_ = approvalLevel_;
    }

    public void setApprovalAmount(long approvalAmount_) {
        this.approvalAmount_ = approvalAmount_;
    }

    @Override
    public String toString() {
        return "InvoiceApprovalTier{" +
                "approvalLevel_=" + approvalLevel_ +
                ", approvalAmount_=" + approvalAmount_ +
                '}';
    }
}
