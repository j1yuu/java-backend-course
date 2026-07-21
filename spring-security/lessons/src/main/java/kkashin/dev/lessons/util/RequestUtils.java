package kkashin.dev.lessons.util;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class RequestUtils {

    public Pageable normalizePageable(Integer pageSize, Integer pageOffset) {
        var normalizedPageSize = pageSize != null ? Math.max(1, pageSize) : Constants.PAGE_SIZE;
        var normalizedPageOffset = pageOffset != null ? Math.max(0, pageOffset) : Constants.PAGE_OFFSET;

        return Pageable.ofSize(normalizedPageSize).withPage(normalizedPageOffset);
    }
}
