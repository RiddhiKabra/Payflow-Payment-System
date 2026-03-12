import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionModelTs } from './transaction.model.ts';

describe('TransactionModelTs', () => {
  let component: TransactionModelTs;
  let fixture: ComponentFixture<TransactionModelTs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionModelTs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionModelTs);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
