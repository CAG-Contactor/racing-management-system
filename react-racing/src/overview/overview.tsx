import * as React from "react"

export class Overview extends React.Component {

  render() {
    return (
      <div>
        <h1>C.A.G Maze Runner</h1>
        <div className="text-justify">
          <p>
            Tävlingen går ut på att köra Zumo-bandvagnen runt banan på snabbaste tid.
          </p>
          <p>
            Mellanstationen är en måltavla som man måste träffa med lasern för att loppet skall godkännas.
          </p>
          <p>
            För att vara med skall man registrera en användare och sedan anmäla sig till tävlingen.
            När det är dags att köra får man en notis i appen.
          </p>
        </div>
      </div>
    )
  }
}

