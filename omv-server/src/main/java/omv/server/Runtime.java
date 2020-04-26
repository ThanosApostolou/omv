package omv.server;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Runtime {
    public String start_datetime;

    public Runtime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        start_datetime = formatter.format(new Date());
        start_datetime = start_datetime.toString();
    }
}