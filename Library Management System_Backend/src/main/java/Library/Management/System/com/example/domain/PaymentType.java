package Library.Management.System.com.example.domain;

public enum PaymentType {
    FINE,MEMBERSHIP,

    /**
    *Payment for damaged book penalty
    */
    DAMAGED_BOOK_PENALTY,
    LOST_BOOK_PENALTY,
    /**
     * Redfund issued to user
     */

    REFUND
}
