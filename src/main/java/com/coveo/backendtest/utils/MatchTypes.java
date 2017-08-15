package com.coveo.backendtest.utils;

/**
 * The enum describes how a result is obtained from the database.
 *
 * Currently only exact match and prefix match are implemented though they are not distinguished in the weighting process.
 */

public enum  MatchTypes {
    EXACT_MATCH,
    PREFIX_MATCH,
    ALTERNATIVE_NAME_MATCH,
    FUZZY_MATCH;
}
