package cn.learn.event;


import cn.learn.context.eventsystem.event.ApplicationContextEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomEvent extends ApplicationContextEvent {

    private Long id;
    private String message;

    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

}
