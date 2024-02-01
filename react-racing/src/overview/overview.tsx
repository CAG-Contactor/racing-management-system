import * as React from "react"
import {Animated} from "react-animated-css";

export class Overview extends React.Component {

  render() {
    return (
      <div className="container">
        <div className="row justify-content-center">
          <Animated animationIn="bounceInLeft" animationOut="fadeOut" isVisible={true}>
            <h1>CAG Racing System</h1>
            <div className="text-justify">
              <p>
                In this game, you have to push three different bricks into three different gates with the help of a robot as fast as possible by using a PlayStation controller. The time will start as soon as you push the first brick through gate number 1. Thereafter you will receive a split time when you push the second brick through gate number 2. When you push the third brick through gate number 3,
                then you have reached the finish line and get your finish time.
              </p>
              <p>
                To participate in the competition you need to register a user, if you are the first registered person, you will end up directly in the current race,
                otherwise you end up in the racing queue. When it is your turn to race, you will see a notification on the top of the page.
              </p>
              This game is built using technologies we have been learning at our techdays through the last year.
              For more information, visit us at the booth.
              You can find more information about us here: <a href=""
                                                              onClick={this.openCagWebpage}>https://www.cag.se/c-a-g-contactor/</a>
            </div>
          </Animated>
        </div>
      </div>
    )
  }

  openCagWebpage = () => {
    window.open("https://www.cag.se/c-a-g-contactor/", "_blank");
  }
}

