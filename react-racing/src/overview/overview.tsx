import * as React from "react"
import {Animated} from "react-animated-css";

export class Overview extends React.Component {

  render() {
    return (
      <div>
        <Animated animationIn="bounceInLeft" animationOut="fadeOut" isVisible={true}>
          <h1>C.A.G Maze Runner</h1>
          <div className="text-justify">
            <p>
              Tävlingen kommer att avslöjas i C.A.Gs monter på JFokus 2019
            </p>
            <p>
              För att vara med skall man registrera en användare och sedan anmäla sig till tävlingen.
              När det är dags att köra får man en notis i appen.
            </p>
          </div>
        </Animated>
      </div>
    )
  }
}

