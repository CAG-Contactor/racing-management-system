import {AdminFrontendPage} from './app.po';

describe('admin-frontend App', function() {
  let page: AdminFrontendPage;

  beforeEach(() => {
    page = new AdminFrontendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
