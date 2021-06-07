package co.casterlabs.brimeapijava.types;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeCategoryWithStreams {
    private BrimeCategory category;

    private List<BrimeStream> streams;

}
