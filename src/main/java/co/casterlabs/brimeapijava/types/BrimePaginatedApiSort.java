package co.casterlabs.brimeapijava.types;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BrimePaginatedApiSort {
    ASECENDING("asc"),
    DESCENDING("desc");

    private String value;

    @Override
    public String toString() {
        return this.value;
    }

}
