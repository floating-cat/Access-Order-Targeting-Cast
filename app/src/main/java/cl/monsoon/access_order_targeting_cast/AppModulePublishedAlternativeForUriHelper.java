package cl.monsoon.access_order_targeting_cast;

import cl.monsoon.access_order_targeting_cast.di.AppScope;
import cl.monsoon.access_order_targeting_cast.util.UriHelper;
import cl.monsoon.access_order_targeting_cast.util.UriHelperImpl;
import dagger.Binds;
import dagger.Module;

@Module
abstract class AppModulePublishedAlternativeForUriHelper {
    @Binds
    @AppScope
    abstract UriHelper provideUriHelper(UriHelperImpl uriHelper);
}
