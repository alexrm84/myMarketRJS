package alexrm84.utils;

import alexrm84.entities.Product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ViewHistory {

    public static String add(String viewsHistory, Long id) {
        if (viewsHistory==null){
            viewsHistory = String.valueOf(id);
        } else {
            if (!getIDsList(viewsHistory).contains(id)) {
                viewsHistory = viewsHistory + "z" + id;
            }
        }
        return viewsHistory;
    }

    public static List<Long> getIDsList(String viewsHistory) {
        List<Long> viewHistoryIds = null;
        if (viewsHistory!=null){
            viewHistoryIds = Arrays.stream(viewsHistory.split("z"))
                    .map(Long::valueOf).distinct().collect(Collectors.toList());
        }
        return viewHistoryIds;
    }
}
