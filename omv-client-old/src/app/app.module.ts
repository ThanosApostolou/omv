import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import {FlexLayoutModule} from '@angular/flex-layout';

import { AppComponent } from './app.component';
import { Header } from '../components/header.component';
import { Footer } from '../components/footer.component';
import { Home } from '../views/home.component';
import { RunVisualizer } from '../views/runvisualizer.component';
import { About } from '../views/about.component';
import { Settings } from '../views/settings.component';
import { User } from '../views/user.component';
import { NotFound } from '../views/notfound.component';

@NgModule({
    declarations: [
        AppComponent,
        Header,
        Footer,
        Home,
        RunVisualizer,
        About,
        Settings,
        User,
        NotFound
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatTabsModule,
        MatMenuModule,
        MatIconModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatGridListModule,
        MatDividerModule,
        MatListModule,
        FlexLayoutModule,
        MatTooltipModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
    title="test";
}
