import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryElementComponent } from './category-element.component';

describe('CategoryElementComponent', () => {
  let component: CategoryElementComponent;
  let fixture: ComponentFixture<CategoryElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryElementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategoryElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
