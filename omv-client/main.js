const { app, BrowserWindow, Menu } = require('electron');

let win=null;

function createWindow () {
    Menu.setApplicationMenu(null);
    // Create the browser window.
    win = new BrowserWindow({
        minHeight: 300,
        minWidth: 300,
        width: 700,
        height: 700,
        icon: `file://${__dirname}/dist/omv-client/assets/favicon.ico`
    });

    win.loadURL(`file://${__dirname}/dist/omv-client/index.html`);

    // uncomment below to open the DevTools.
    // win.webContents.openDevTools();

    // Event when the window is closed.
    win.on('closed', function () {
        win = null;
    });
}

// Create window on electron intialization
app.on('ready', createWindow);

// Quit when all windows are closed.
app.on('window-all-closed', function () {
    // On macOS specific close process
    if (process.platform !== 'darwin') {
        app.quit();
    }
    });
    app.on('activate', function () {
    // macOS specific close process
    if (win === null) {
        createWindow();
    }
});