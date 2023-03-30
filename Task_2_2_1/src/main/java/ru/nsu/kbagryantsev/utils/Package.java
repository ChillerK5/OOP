package ru.nsu.kbagryantsev.utils;

/**
 * Data wrapper. Includes a header with package status.
 *
 * @param header {@code Header}
 * @param data   package data
 */
public record Package(Header header, Object data) {
    /**
     * Instantiates a package with default {@link Header#CONTINUE} header.
     *
     * @param data package data
     */
    public Package(final Object data) {
        this(Header.CONTINUE, data);
    }

    /**
     * Instantiates an empty package with a given header.
     *
     * @param header package header
     */
    private Package(final Header header) {
        this(header, new Object());
    }

    /**
     * Returns an empty package with {@link Header#TERMINATE} header.
     *
     * @return terminating {@code Package}
     */
    public static Package terminating() {
        return new Package(Header.TERMINATE);
    }

    /**
     * If a package has {@link Header#TERMINATE} header, returns true,
     * otherwise false.
     *
     * @return {@code true} if has {@link Header#TERMINATE} header, otherwise
    {@code false}
     */
    public boolean isTerminating() {
        return this.header == Header.TERMINATE;
    }

    /**
     * Package header. Signals package status.
     */
    public enum Header {
        /**
         * Package transmitted with no extra information.
         */
        CONTINUE,
        /**
         * Poisoning package. Signals the receiver to terminate.
         */
        TERMINATE
    }
}
