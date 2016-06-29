package cl.monsoon.access_order_targeting_cast;

import cl.monsoon.access_order_targeting_cast.di.AppScope;
import cl.monsoon.access_order_targeting_cast.util.FakeUriHelper;
import cl.monsoon.access_order_targeting_cast.util.UriHelper;
import dagger.Binds;
import dagger.Module;

@Module
abstract class AndroidTestAppModulePublishedAlternativeForUriHelper {
    @Binds
    @AppScope
    abstract UriHelper provideUriHelper(FakeUriHelper fakeUriHelper);
}
