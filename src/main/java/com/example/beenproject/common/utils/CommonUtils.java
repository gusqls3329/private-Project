package com.example.beenproject.common.utils;


import com.example.beenproject.common.exception.ErrorCode;
import com.vane.badwordfiltering.BadWordFiltering;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Component
@Slf4j
public abstract class CommonUtils {

    private static final BadWordFiltering badWordFiltering = new BadWordFiltering();
    public static List<String> filterWord;


    public static int paymentStatusResolver(int beforeStatus) {

        return beforeStatus / 10 == 0 ? beforeStatus : beforeStatus / 10;
    }

    public static boolean ifContainsBadWordTrue(String... words) {
        for (int i = 0; i < words.length; i++) {
            for (String filter : filterWord) {
                if (words[i].contains(filter)) {
                    words[i] = words[i].replace(filter, "");
                }
            }
        }
        log.debug("[CommonUtils.ifContainsBadWordTrue()] words = {}", Arrays.toString(words));
        return Arrays.stream(words).anyMatch(badWordFiltering::blankCheck);
    }

    public static void ifContainsBadWordThrow(Class<? extends RuntimeException> ex, ErrorCode err, String... words) {
        if (ifContainsBadWordTrue(words)) thrown(ex, err);
    }

    public static void ifContainsBadWordThrow(Class<? extends RuntimeException> ex, ErrorCode err,
                                              List<String> collectionWord, String... words) {
        if (ifContainsBadWordTrue(words)) thrown(ex, err);
        for (String word : collectionWord) {
            if (ifContainsBadWordTrue(word)) thrown(ex, err);
        }
    }


    public static void ifContainsBadWordThrow(Class<? extends RuntimeException> ex, ErrorCode err, List<String> words) {
        for (String word : words) {
            ifContainsBadWordThrow(ex, err, word);
        }
    }

    public static String ifContainsBadWordChangeThat(String word) {
        return badWordFiltering.change(word);
    }


    public static boolean notBetweenChecker(LocalDate refStartDate, LocalDate refEndDate,
                                            LocalDate newStartDate, LocalDate newEndDate) {

        return newStartDate.isBefore(refStartDate) && newEndDate.isBefore(refStartDate)
               || newStartDate.isAfter(refEndDate) && newEndDate.isAfter(refEndDate);
    }


    public static void ifObjNullOrZeroThrow(Class<? extends RuntimeException> ex, ErrorCode err, Integer n) {
        if (n == null || n == 0) {
            thrown(ex, err);
        }
    }


    public static void ifFalseThrow(Class<? extends RuntimeException> ex, ErrorCode err, boolean b) {
        if (!b) thrown(ex, err);
    }


    public static <T> void checkSizeIfOverLimitNumThrow(Class<? extends RuntimeException> ex, ErrorCode err, Stream<T> collection,
                                                        int limitNum) {
        if (collection.count() > limitNum) {
            thrown(ex, err);
        }
    }

    public static <T> void checkSizeIfOverLimitNumThrow(Class<? extends RuntimeException> ex, ErrorCode err,
                                                        String reason, Stream<T> collection, int limitNum) {
        if (collection.count() > limitNum) {
            thrown(ex, err, reason);
        }
    }


    public static <T> void checkSizeIfUnderLimitNumThrow(Class<? extends RuntimeException> ex, ErrorCode err,
                                                         Stream<T> collection,
                                                         int limitNum) {
        if (collection.count() < limitNum) {
            thrown(ex, err);
        }
    }




    public static void ifAfterThrow(Class<? extends RuntimeException> ex, ErrorCode err, LocalDate expectedAfter,
                                    LocalDate expectedBefore) {
        if (expectedBefore.isAfter(expectedAfter)) {
            thrown(ex, err);
        }
    }

    public static void ifBeforeThrow(Class<? extends RuntimeException> ex, ErrorCode err, LocalDate expectedAfter,
                                     LocalDate expectedBefore) {
        if (expectedAfter.isBefore(expectedBefore)) {
            thrown(ex, err);
        }
    }

    public static boolean ifAllNullReturnFalse(Object... objs) {
        for (Object obj : objs) {
            if (obj != null)
                return true;
        }
        return false;
    }


    public static Integer getDepositFromPer(Integer price, Integer percent) {
        return (int) (price * percent * 0.01);
    }

    public static Integer getDepositPerFromPrice(Integer price, Integer deposit) {
        return (price / deposit);
    }

    public static void ifAnyNullThrow(Class<? extends RuntimeException> ex, ErrorCode err, Object... objs) {
        Arrays.stream(objs).forEach(o -> {
            if (o == null) {
                thrown(ex, err);
            }
        });
    }

    public static void ifAnyNullThrow(Class<? extends RuntimeException> ex, ErrorCode err, String reason, Object... objs) {
        Arrays.stream(objs).forEach(o -> {
            if (o == null) {
                thrown(ex, err);
            }
        });
    }

    public static void ifAnyNotNullThrow(Class<? extends RuntimeException> ex, ErrorCode err, Object... objs) {
        Arrays.stream(objs).forEach(o -> {
            if (o != null) {
                thrown(ex, err);
            }
        });
    }

    public static void ifAllNullThrow(Class<? extends RuntimeException> ex, ErrorCode err, Object... objs) {
        for (Object obj : objs) {
            if (obj != null) {
                return;
            }
        }
        thrown(ex, err);
    }

    public static <T> void ifAllNullThrow(Class<? extends RuntimeException> ex, ErrorCode err, Stream<T> stream, Object... objs) {
        if (stream.findAny().isPresent()) return;
        for (Object obj : objs) {
            if (obj != null) {
                return;
            }
        }
        thrown(ex, err);
    }

    public static void ifAllNotNullThrow(Class<? extends RuntimeException> ex, ErrorCode err, Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                return;
            }
        }
        thrown(ex, err);
    }

    public static void checkNullOrZeroIfCollectionThrow(Class<? extends RuntimeException> ex, ErrorCode err, Object instance) {
        ifAnyNullThrow(ex, err, instance);
        if (instance instanceof Collection<?>) {
            checkSizeIfUnderLimitNumThrow(ex, err,
                    ((Collection<?>) instance).stream(), 1);
        }
    }

    public static boolean checkNullOrZeroIfCollectionReturnFalse(Class<? extends RuntimeException> ex, ErrorCode err,
                                                                 Object instance) {
        if (!ifAnyNullReturnFalse(ex, err, instance)) return false;
        if (instance instanceof Collection<?>) {
            return checkSizeIfUnderLimitNumReturnFalse(ex, err,
                    ((Collection<?>) instance).stream(), 1);
        }
        return true;
    }

    public static <T> boolean checkSizeIfUnderLimitNumReturnFalse(Class<? extends RuntimeException> ex, ErrorCode err,
                                                                  Stream<T> collection,
                                                                  int limitNum) {
        return collection.count() >= limitNum;
    }

    public static boolean ifAnyNullReturnFalse(Class<? extends RuntimeException> ex, ErrorCode err, Object... objs) {
        for (Object obj : objs) {
            if (obj == null) return false;
        }
        return true;
    }

    //

    /**
     * 예외 throw 메소드 (내부)
     *
     * @param ex
     * @param err
     */
    private static void thrown(Class<? extends RuntimeException> ex,
                               ErrorCode err) {
        try {
            throw ex.getDeclaredConstructor(ErrorCode.class)
                    .newInstance(err);
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static void thrown(Class<? extends RuntimeException> ex,
                               ErrorCode err, String reason) {
        try {
            throw ex.getDeclaredConstructor(ErrorCode.class, String.class)
                    .newInstance(err, reason);
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
