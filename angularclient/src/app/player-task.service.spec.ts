import { TestBed } from '@angular/core/testing';

import { PlayerTaskService } from './player-task.service';

describe('PlayerTaskService', () => {
  let service: PlayerTaskService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlayerTaskService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
