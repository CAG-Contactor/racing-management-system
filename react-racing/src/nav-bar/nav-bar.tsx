import * as React from 'react';
import './nav-bar.css';

export type NavBarSelections = 'Overview' | 'Queue' | 'MyRaces' | 'Leaderboard' | 'CurrentRace';

export interface NavBarProps {
  readonly currentSelection: NavBarSelections
  readonly onChangedSelection: (viewSelection: NavBarSelections) => void
}

export const NavBar = (props: NavBarProps) => {
  const {currentSelection, onChangedSelection} = props;
  return (
    <nav className="navbar navbar-expand-md navbar-dark mb-3">

      <button style={{marginBottom: 10}} className="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span className="navbar-toggler-icon"/>
      </button>

      <div style={{textAlign:"left"}} className="collapse navbar-collapse justify-content-center" id="collapsibleNavbar">
        <ul className="navbar-nav">
          <li className="nav-item">
            <a className={navAnchorClass(currentSelection === 'Overview')}
               href="javascript:void 0"
               onClick={changeSelection('Overview')}>Overview</a>
          </li>
          <li className="nav-item">
            <a className={navAnchorClass(currentSelection === 'Queue')}
               href="javascript:void 0"
               onClick={changeSelection('Queue')}>Queue</a>
          </li>
          <li className="nav-item">
            <a className={navAnchorClass(currentSelection === 'Leaderboard')}
               href="javascript:void 0"
               onClick={changeSelection('Leaderboard')}>Leaderboard</a>
          </li>
          <li>
            <a className={navAnchorClass(currentSelection === 'CurrentRace')}
               href="javascript:void 0"
               onClick={changeSelection('CurrentRace')}>Current Race</a>
          </li>
          <li>
            <a className={navAnchorClass(currentSelection === 'MyRaces')}
               href="javascript:void 0"
               onClick={changeSelection('MyRaces')}>My Races</a>
          </li>
        </ul>
      </div>
    </nav>
  );

  function changeSelection(newSelection: NavBarSelections) {
    return () => onChangedSelection(newSelection)
  }
};

function navAnchorClass(active: boolean) {
  return `nav-link ${active ? 'active' : ''}`;
}

