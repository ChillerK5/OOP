package ru.nsu.kbagryantsev;

public record Teacher(String firstName, String lastName, String patronymic,
                      Integer signature) {
    /**
     * Creates Teacher instance by full name in case of no patronymic.
     *
     * @param pFirstName first name
     * @param pLastName last name
     * @param pSignature signature
     */
    public Teacher(final String pFirstName, final String pLastName,
                   final int pSignature) {
        this(pFirstName, pLastName, null, pSignature);
    }

    /**
     * Verifies given signature with model one.
     *
     * @param s asserted signature
     * @return true if equal
     */
    public boolean verifySignature(final Integer s) {
        return signature.equals(s);
    }
}
