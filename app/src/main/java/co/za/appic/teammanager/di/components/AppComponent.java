package co.za.appic.teammanager.di.components;

import javax.inject.Singleton;
import co.za.appic.teammanager.MyApplication;
import co.za.appic.teammanager.di.modules.AppModule;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MyApplication myApplication);
}