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
    <nav className="nav nav-masthead justify-content-center mb-3">
      <nav className="nav nav-masthead justify-content-center">
        <a className={navAnchorClass(currentSelection === 'Overview')}
           href="javascript:void 0"
           onClick={changeSelection('Overview')}>Overview</a>
        <a className={navAnchorClass(currentSelection === 'Queue')}
           href="javascript:void 0"
           onClick={changeSelection('Queue')}>Queue</a>
        <a className={navAnchorClass(currentSelection === 'Leaderboard')}
           href="javascript:void 0"
           onClick={changeSelection('Leaderboard')}>Leader board</a>
        <a className={navAnchorClass(currentSelection === 'CurrentRace')}
           href="javascript:void 0"
           onClick={changeSelection('CurrentRace')}>Current race</a>
        <a className={navAnchorClass(currentSelection === 'MyRaces')}
           href="javascript:void 0"
           onClick={changeSelection('MyRaces')}>My races</a>
      </nav>
    </nav>
  );

  function changeSelection(newSelection: NavBarSelections) {
    return () => onChangedSelection(newSelection)
  }
};

function navAnchorClass(active: boolean) {
  return `nav-link ${active ? 'active' : ''}`;
}

