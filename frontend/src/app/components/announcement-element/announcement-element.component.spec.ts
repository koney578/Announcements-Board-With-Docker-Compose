import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnouncementElementComponent } from './announcement-element.component';

describe('AnnouncementElementComponent', () => {
  let component: AnnouncementElementComponent;
  let fixture: ComponentFixture<AnnouncementElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnouncementElementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnnouncementElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
